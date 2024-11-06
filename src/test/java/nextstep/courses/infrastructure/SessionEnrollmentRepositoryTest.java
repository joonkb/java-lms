package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.users.domain.NsUser;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionEnrollmentRepositoryTest {

    private static final long SAVED_SESSION_ID = 4L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private JdbcUserRepository userRepository;

    private JdbcSessionEnrollmentRepository sessionEnrollmentRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        userRepository = new JdbcUserRepository(jdbcTemplate);
        sessionEnrollmentRepository = new JdbcSessionEnrollmentRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 Session을 수강하고 있는 학생들을 조회한다.")
    void enrollAndFindStudents() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        sessionRepository.save(session);

        NsUser user1 = userRepository.findByUserId("javajigi").orElseThrow();
        NsUser user2 = userRepository.findByUserId("sanjigi").orElseThrow();

        int result = 0;
        result += sessionEnrollmentRepository.enrollStudent(session.getId(), user1.getId());
        result += sessionEnrollmentRepository.enrollStudent(session.getId(), user2.getId());

        List<SessionStudent> enrolledUsers = sessionEnrollmentRepository.findStudentsBySessionId(session.getId());
        assertThat(enrolledUsers).hasSize(result);
    }

    @Test
    @DisplayName("해당 세션에 신청 대기중인 학생을 조회한다.")
    void findPendingStudents() {

        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        sessionRepository.save(session);

        NsUser user1 = userRepository.findByUserId("javajigi").orElseThrow();
        sessionEnrollmentRepository.enrollStudent(session.getId(), user1.getId());

        List<SessionStudent> pendingStudents = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(session.getId(), EnrollmentStatus.PENDING);
        assertThat(pendingStudents).hasSize(1);
    }

    @Test
    @DisplayName("해당 세션에 신청 대기중인 학생을 조회하여, 선발된 인원을 승인한다.")
    void findPendingStudentsAndApprove() {
        List<SessionStudent> pendingStudents = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(SAVED_SESSION_ID, EnrollmentStatus.PENDING);
        LOGGER.info("pendingStudents = {} ", pendingStudents);
        assertThat(pendingStudents).hasSize(1);

        SessionStudent pendingStudent = pendingStudents.get(0);
        pendingStudent.updateEnrollmentStatus(EnrollmentStatus.APPROVED);
        sessionEnrollmentRepository.updateStudentEnrollmentStatus(pendingStudent);

        List<SessionStudent> approvedStudents = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(SAVED_SESSION_ID, EnrollmentStatus.APPROVED);
        assertThat(pendingStudents).hasSize(1);
        LOGGER.info("approvedStudents = {} ", approvedStudents);
    }

    @Test
    @DisplayName("해당 세션에 신청 대기중인 학생을 조회하여, 선발된 인원을 취소한다.")
    void findPendingStudentsAndCancel() {
        List<SessionStudent> pendingStudents = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(SAVED_SESSION_ID, EnrollmentStatus.PENDING);
        LOGGER.info("pendingStudents = {} ", pendingStudents);
        assertThat(pendingStudents).hasSize(1);

        SessionStudent pendingStudent = pendingStudents.get(0);
        pendingStudent.updateEnrollmentStatus(EnrollmentStatus.REJECTED);
        sessionEnrollmentRepository.updateStudentEnrollmentStatus(pendingStudent);

        List<SessionStudent> canceledStudents = sessionEnrollmentRepository.findStudentsByEnrollmentStatus(SAVED_SESSION_ID, EnrollmentStatus.APPROVED);
        assertThat(pendingStudents).hasSize(1);
        LOGGER.info("canceledStudents = {} ", canceledStudents);
    }
}
