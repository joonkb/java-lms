package nextstep.payments.service;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaymentService {
    public Payment payment(Session session, NsUser user) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        if (session instanceof PaidSession) {
            return new Payment(1L, session.getId(), user.getId(), ((PaidSession) session).getPrice());
        }
        return new Payment(1L, session.getId(), user.getId(), 0L);
    }
}
