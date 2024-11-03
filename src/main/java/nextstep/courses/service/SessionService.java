package nextstep.courses.service;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "coverImageRepository")
    private CoverImageRepository coverImageRepository;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Transactional
    public void enrollSession(NsUser loginUser, long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        Payment payment = paymentService.payment(session, loginUser);
        session.enroll(loginUser, payment);
        sessionRepository.enroll(sessionId, loginUser.getId());
    }

    @Transactional
    public void uploadSessionImage(CoverImage image, long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);
        session.uploadCoverImage(image);
        coverImageRepository.upload(image);
    }
}
