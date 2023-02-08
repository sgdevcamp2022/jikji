package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, String> {

}
