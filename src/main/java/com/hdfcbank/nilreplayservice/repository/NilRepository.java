package com.hdfcbank.nilreplayservice.repository;

import com.hdfcbank.nilreplayservice.model.MsgEventTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class NilRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<MsgEventTracker> fetchReplayBatch(String status, int limit) {
        String sql = """
                    SELECT * FROM network_il.msg_event_tracker
                    WHERE status = :status
                    ORDER BY created_time ASC
                    LIMIT :limit
                """;

        Map<String, Object> params = Map.of("status", status, "limit", limit);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            MsgEventTracker event = new MsgEventTracker();
            event.setMsgId(rs.getString("msg_id"));
            event.setSource(rs.getString("source"));
            event.setTarget(rs.getString("target"));
            event.setFlowType(rs.getString("flow_type"));
            event.setMsgType(rs.getString("msg_type"));
            event.setOrgnlReq(rs.getString("original_req"));
            event.setOrgnlReqCount(getInteger(rs, "original_req_count"));
            event.setConsolidateAmt(rs.getBigDecimal("consolidate_amt"));
            event.setIntermediateReq(rs.getString("intermediate_req"));
            event.setIntermediateCount(getInteger(rs, "intemdiate_count"));
            event.setStatus(rs.getString("status"));
            event.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
            event.setModifiedTimestamp(rs.getTimestamp("modified_timestamp").toLocalDateTime());
            return event;
        });
    }

    public void markAsReplayed(String msgId, String source, String target) {
        String sql = """
                    UPDATE network_il.msg_event_tracker
                    SET status = 'REPLAYED',
                        modified_timestamp = CURRENT_TIMESTAMP
                    WHERE msg_id = :msgId AND source = :source AND target = :target
                """;

        Map<String, Object> params = Map.of("msgId", msgId, "source", source, "target", target);

        jdbcTemplate.update(sql, params);
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    private Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {
        BigDecimal val = rs.getBigDecimal(columnLabel);
        return val != null ? val.intValue() : null;
    }
}
