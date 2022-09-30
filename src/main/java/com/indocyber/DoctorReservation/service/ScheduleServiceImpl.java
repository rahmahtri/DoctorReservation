package com.indocyber.DoctorReservation.service;

import com.indocyber.DoctorReservation.dto.schedule.ListScheduleDTO;
import com.indocyber.DoctorReservation.dto.schedule.UpsertScheduleDTO;
import com.indocyber.DoctorReservation.entity.Doctor;
import com.indocyber.DoctorReservation.entity.Schedule;
import com.indocyber.DoctorReservation.repository.DoctorRepository;
import com.indocyber.DoctorReservation.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private final int rowsInPage = 2;


    @Override
    public List<Schedule> getAllSchedule() {
        List<Schedule> findAllSchedule = scheduleRepository.findAllSchedule();
        return findAllSchedule;
    }

    @Override
    public List<Schedule> getSchedule(Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("Id"));
        List<Schedule> schedules = scheduleRepository.findAllSchedule(pagination);
        return schedules;
    }

    @Override
    public UpsertScheduleDTO findScheduleByID(Long id) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);
        Schedule entity = findSchedule.get();
        UpsertScheduleDTO scheduleDTO = new UpsertScheduleDTO(
                entity.getId(),
                entity.getDate(),
                entity.getStartTreat(),
                entity.getEndTreat(),
                entity.getDoctor().getId(),
                entity.getIsAvailable()
        );
        return scheduleDTO;
    }

    @Override
    public Long saveSchedule(UpsertScheduleDTO dto) {
        Optional<Doctor> findDoctor = doctorRepository.findById(dto.getDoctorId());
        Doctor doctor = findDoctor.get();

        Boolean available = true;
        Schedule entity = new Schedule(
                dto.getId(),
                dto.getDate(),
                dto.getStartTreat(),
                dto.getEndTreat(),
                doctor,
                available
        );

        Schedule respond = scheduleRepository.save(entity);
        return respond.getId();
    }

    @Override
    public void saveSchedule(UpsertScheduleDTO dto, Long id) {
        Optional<Doctor> findDoctor = doctorRepository.findById(dto.getDoctorId());
        Doctor doctor = findDoctor.get();

        Boolean available = true;

        Optional<Schedule> findSchedule = scheduleRepository.findById(id);
        Schedule schedule = findSchedule.get();

        schedule.setId(id);
        schedule.setDate(dto.getDate());
        schedule.setStartTreat(dto.getStartTreat());
        schedule.setEndTreat(dto.getEndTreat());
        schedule.setDoctor(doctor);
        schedule.setIsAvailable(available);

        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> addMultipleSchedule(ListScheduleDTO scheduleDTO) {

        List<Schedule> schedules = scheduleDTO.getScheduleDTOS()
                .stream()
                .map(s -> {

                    Optional<Doctor> findDoctor = doctorRepository.findById(s.getDoctorId());
                    Doctor doctor = findDoctor.get();

                    Boolean available = true;
                    return new Schedule(
                            s.getId(),
                            s.getDate(),
                            s.getStartTreat(),
                            s.getEndTreat(),
                            doctor,
                            available);
                })
                .collect(Collectors.toList());

        return scheduleRepository.saveAll(schedules);
    }

    @Override
    public Schedule findById(Long scheduleId) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(scheduleId);
        Schedule schedule = findSchedule.get();
        return schedule;
    }
}
