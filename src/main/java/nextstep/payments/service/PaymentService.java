package nextstep.payments.service;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentService {
    public Payment payment(Session session, NsUser user) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment(1L, session.getId(), user.getId(), session.getPrice());
    }
}
