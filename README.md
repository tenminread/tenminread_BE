# 겨울잠 10분독서 백엔드 레포지토리

## 📌 Branch 전략
- 기본 브랜치: `develop`
- 배포/서버 연동 시에만 `main` 사용
- 작업 브랜치: `feature/<이슈번호>-<키워드>` (예: `feature/#12-reading-log`)

## 🔀 PR 규칙
- base = `develop`
- 템플릿 사용, 관련 이슈 링크 (예: closes #12)
- Squash merge 권장

## 📑 커밋 컨벤션
- 형식: **`[기능] 제목 - 부연설명`**
  - `[기능] 제목` ⟶ 필수 (15자 이내 권장)
  - `- 부연설명` ⟶ 선택
- `[기능]` 타입
  - `init` `build` `ci` `docs` `feat` `fix` `refactor` `chore` `test`
