package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<NsUser> students = sessionEnrollmentRepository.findStudentsBySessionId(sessionId);
        session.enroll(students, payment);
        sessionEnrollmentRepository.enrollStudent(sessionId, loginUser.getId());
    }

    @Transactional
    public void uploadSessionImage(CoverImage image, long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        session.uploadCoverImage(image);
        coverImageRepository.upload(image);
    }
}
