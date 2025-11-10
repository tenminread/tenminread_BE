-- MAIN DB 초기 스키마 (필요시 자유롭게 확장)
-- 안전한 더미 테이블: 삭제해도 무방합니다.
CREATE TABLE IF NOT EXISTS __keep_main (
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
