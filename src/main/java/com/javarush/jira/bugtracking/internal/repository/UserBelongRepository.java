package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserBelongRepository extends BaseRepository<UserBelong> {

    @Query(value = "SELECT ub.id FROM user_belong ub WHERE ub.user_id=:userId AND ub.object_id=:taskId " +
            "AND ub.object_type=:type AND ub.user_type_code=:userTypeCode", nativeQuery = true)
    Long getUserBelongByUserIdAndObjectIdAndObjectType(Long userId, Long taskId, int type, String userTypeCode);

}
