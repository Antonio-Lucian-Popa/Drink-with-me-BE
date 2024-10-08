package com.asusoftware.Drink_with_me.user_api.repository;

import com.asusoftware.Drink_with_me.user_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE id <> :userId AND id NOT IN (SELECT user_id FROM user_followers WHERE follower_id = :userId) ORDER BY RANDOM() LIMIT 5", nativeQuery = true)
    List<User> findRandomUsers(UUID userId);


    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT(:name, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT(:name, '%'))")
    List<User> findByUsernameStartingWith(String name);

    @Query("SELECT u FROM User u JOIN u.followers f WHERE f.id = :followerId AND u.id IN :followedIds")
    List<User> findIfFollowing(@Param("followerId") UUID followerId, @Param("followedIds") List<UUID> followedIds);

    /**
     * Retrieve user followers that follow the userId
     * @param userId of the user that have followers
     * @return a list of followers for the current user id
     */
    @Query("SELECT f FROM User u JOIN u.followers f WHERE u.id = :userId")
    List<User> findFollowersByUserId(@Param("userId") UUID userId);

    /**
     * Retrieve user following users
     * @param userId of the user that follow users
     * @return a list of users that user follow
     */
    @Query("SELECT f FROM User u JOIN u.following f WHERE u.id = :userId")
    List<User> findFollowingByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(f) FROM User u JOIN u.followers f WHERE u.id = :userId")
    long countFollowersByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(f) FROM User u JOIN u.following f WHERE u.id = :userId")
    long countFollowingByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(f) > 0 FROM User u JOIN u.followers f WHERE u.id = :profileUserId AND f.id = :currentUserId")
    boolean isUserFollowing(@Param("profileUserId") UUID profileUserId, @Param("currentUserId") UUID currentUserId);
}
