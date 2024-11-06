package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionEnrollmentRepository")
    private SessionEnrollmentRepository sessionEnrollmentRepository;

    @Resource(name = "coverImageRepository")
    private CoverImageRepository coverImageRepository;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Transactional
    public void enrollSession(NsUser loginUser, long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        Payment payment = paymentService.payment(session, loginUser);
        List<SessionStudent> students = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(sessionId, EnrollmentStatus.PENDING);
        session.enroll(students, payment);
        sessionEnrollmentRepository.enrollStudent(sessionId, loginUser.getId());
    }

    @Transactional
    public void uploadSessionImage(CoverImage image, long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        coverImageRepository.upload(session.getId(), image);
    }

    @Transactional
    public void updateEnrollmentStatus(SessionStudent student, String status) {
        SessionStudent findStudent = sessionEnrollmentRepository.findStudentById(student);
        findStudent.updateEnrollmentStatus(EnrollmentStatus.valueOf(status));
        sessionEnrollmentRepository.updateStudentEnrollmentStatus(student);
    }
}
