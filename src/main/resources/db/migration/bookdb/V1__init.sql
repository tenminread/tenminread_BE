-- BOOK DB 초기 스키마 (필요시 자유롭게 확장)
CREATE TABLE IF NOT EXISTS __keep_book (
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
