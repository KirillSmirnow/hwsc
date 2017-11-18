package org.smirnowku.hwsc.server.service;

import org.springframework.stereotype.Service;

@Service
public class HomeworkService {

//    public void createHomework(long classroomId, String name, Date deadline, Integer subgroupSize) {
//        Classroom classroom = classroomService.getClassroom(classroomId);
//        Homework homework = new Homework(name, deadline, subgroupSize);
//        homeworkRepository.save(homework);
//        classroom.homeworks().add(homework);
//        classroomRepository.save(classroom);
//        classroom.students().forEach(student -> {
//            HomeworkSolution homeworkSolution = new HomeworkSolution(homework);
//            homeworkSolutionRepository.save(homeworkSolution);
//            Assignment assignment = new Assignment(student, homeworkSolution);
//            assignmentRepository.save(assignment);
//        });
//    }

//    public void submitHomework(long userId, long homeworkId) {
//        User student = userService.getUser(userId);
//        Homework homework = homeworkRepository.findOne(homeworkId);
//        Assignment assignment = assignmentRepository.findByStudentAndHomework(student, homework);
//        assignment.setStatus(Assignment.Status.SUBMITTED);
//        assignmentRepository.save(assignment);
//        doHwAssignment();
//    }
//
//    private void doHwAssignment() {
//    }
}
