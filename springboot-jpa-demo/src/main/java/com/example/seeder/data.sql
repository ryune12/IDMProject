-- Run query one by one
-- ============================================================================ 
-- Seeder for m_province, m_branches, and m_stores tables
INSERT INTO m_province (province_name, alt_name, created_at, created_by) VALUES
('Aceh', NULL, NOW(), 'system'),
('Sumatera Utara', 'Sumut', NOW(), 'system'),
('Sumatera Barat', 'Sumbar', NOW(), 'system'),
('Riau', NULL, NOW(), 'system'),
('Kepulauan Riau', 'Kepri', NOW(), 'system'),
('Jambi', NULL, NOW(), 'system'),
('Sumatera Selatan', 'Sumsel', NOW(), 'system'),
('Bengkulu', NULL, NOW(), 'system'),
('Lampung', NULL, NOW(), 'system'),
('Bangka Belitung', NULL, NOW(), 'system'),
('DKI Jakarta', 'Jakarta', NOW(), 'system'),
('Jawa Barat', 'Jabar', NOW(), 'system'),
('Jawa Tengah', 'Jateng', NOW(), 'system'),
('DI Yogyakarta', 'Jogja', NOW(), 'system'),
('Jawa Timur', 'Jatim', NOW(), 'system'),
('Banten', NULL, NOW(), 'system'),
('Bali', NULL, NOW(), 'system'),
('Nusa Tenggara Barat', 'NTB', NOW(), 'system'),
('Nusa Tenggara Timur', 'NTT', NOW(), 'system'),
('Kalimantan Barat', 'Kalbar', NOW(), 'system'),
('Kalimantan Tengah', 'Kalteng', NOW(), 'system'),
('Kalimantan Selatan', 'Kalsel', NOW(), 'system'),
('Kalimantan Timur', 'Kaltim', NOW(), 'system'),
('Kalimantan Utara', 'Kaltara', NOW(), 'system'),
('Sulawesi Utara', 'Sulut', NOW(), 'system'),
('Sulawesi Tengah', 'Sulteng', NOW(), 'system'),
('Sulawesi Selatan', 'Sulsel', NOW(), 'system'),
('Sulawesi Tenggara', 'Sultra', NOW(), 'system'),
('Gorontalo', NULL, NOW(), 'system'),
('Maluku', NULL, NOW(), 'system'),
('Maluku Utara', NULL, NOW(), 'system'),
('Papua', NULL, NOW(), 'system'),
('Papua Barat', NULL, NOW(), 'system'),
('Papua Tengah', NULL, NOW(), 'system'),
('Papua Pegunungan', NULL, NOW(), 'system'),
('Papua Selatan', NULL, NOW(), 'system'),
('Papua Barat Daya', NULL, NOW(), 'system');

-- Generate 100 branches with random province_id between 1 and 37

INSERT INTO m_branches (name, province_id, created_at, created_by) 
SELECT 
    'Branch ' || LPAD((i)::varchar(20), 2, '0') AS name,
    (floor(random() * 37) + 1)::int AS province_id,
	NOW(),
	'system'
FROM generate_series(1, 100) AS s(i);

-- Generate 20,000 stores with various fields, some nullable, and is_whitelist TRUE for first 3 stores

INSERT INTO m_stores (
    name,
    address,
    telp,
    post_code,
    is_whitelist,
    branch_id,
    created_at,
    created_by,
    updated_at,
    updated_by,
    deleted_at,
    deleted_by
)
SELECT
    'Store ' || i AS name,
    CASE WHEN random() > 0.1 THEN 'Street ' || (100 + i)::text ELSE NULL END AS address,
    CASE WHEN random() > 0.2 THEN '08' || floor(random() * 1000000000)::text ELSE NULL END AS telp,
    CASE WHEN random() > 0.3 THEN lpad((10000 + (i % 90000))::text, 5, '0') ELSE NULL END AS post_code,
    CASE 
        WHEN i <= 3 THEN TRUE
        ELSE FALSE
    END AS is_whitelist,
    (1 + (i % 100)) AS branch_id,
    NOW() AS created_at,
    'system' AS created_by,
    NULL AS updated_at,
    NULL AS updated_by,
    NULL AS deleted_at,
    NULL AS deleted_by
FROM generate_series(1, 20000) s(i);

-- ===========================================================================
CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO public.m_users (id, email, name, password, token, username)
VALUES
(1, 'alice@example.com', 'Alice Johnson', crypt('alice123', gen_salt('bf')), 'token_abc123', 'alice'),
(2, 'bob@example.com', 'Bob Smith', crypt('bob123', gen_salt('bf')), 'token_def456', 'bob'),
(3, 'charlie@example.com', 'Charlie Lee', crypt('charlie123', gen_salt('bf')), 'token_ghi789', 'charlie'),
(4, 'diana@example.com', 'Diana Prince', crypt('diana123', gen_salt('bf')), 'token_jkl012', 'diana'),
(5, 'edward@example.com', 'Edward Norton', crypt('edward123', gen_salt('bf')), 'token_mno345', 'edward');
-- ===========================================================================
-- Indexes to optimize queries if needed
-- ===========================================================================
-- 1. m_stores index for is_whitelist and deleted_at filtering
CREATE INDEX idx_stores_whitelist_deleted
ON m_stores (is_whitelist, deleted_at);

-- 2. m_branches index for province join
CREATE INDEX idx_branches_province
ON m_branches (province_id);

-- 3. m_province full-text index on province_name
CREATE INDEX idx_province_name_ft
ON m_province
USING gin (to_tsvector('simple', province_name));
-- ===========================================================================