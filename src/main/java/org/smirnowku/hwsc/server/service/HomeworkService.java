package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.*;
import org.smirnowku.hwsc.server.repository.ClassroomRepository;
import org.smirnowku.hwsc.server.repository.HomeworkRepository;
import org.smirnowku.hwsc.server.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.server.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class HomeworkService {

    @Resource
    private UserService userService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private ClassroomRepository classroomRepository;

    @Resource
    private ProgressRepository progressRepository;

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private HomeworkRepository homeworkRepository;

    public HomeworkSolution getHomeworkSolution(long userId, long homeworkId) {
        User student = userService.getUser(userId);
        Homework homework = homeworkRepository.findOne(homeworkId);
        Progress progress = progressRepository.findByStudentAndHomeworkSolution_Homework(student, homework);
        return progress.homeworkSolution();
    }

    public void createHomework(long classroomId, String name, Date deadline) {
        Classroom classroom = classroomService.getClassroom(classroomId);
        Homework homework = new Homework(name, deadline);
        homeworkRepository.save(homework);
        classroom.homeworks().add(homework);
        classroomRepository.save(classroom);
        classroom.students().forEach(student -> {
            HomeworkSolution homeworkSolution = new HomeworkSolution(homework);
            homeworkSolutionRepository.save(homeworkSolution);
            Progress progress = new Progress(student, homeworkSolution);
            progressRepository.save(progress);
        });
    }
}
