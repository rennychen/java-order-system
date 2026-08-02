# 🛒 E-Commerce Order System (訂單微服務系統)

> 本專案為 Java 後端開發作品集，模擬電商系統的核心訂單業務 logic。

---

## 📌 專案簡介 (Project Overview)

本系統旨在提供穩定、可擴充的訂單處理 API，涵蓋使用者管理、商品訂購、訂單狀態轉變及多表關聯操作。

### 核心功能規劃
- [ ] **User 模組**：使用者註冊、資料維護與身份驗證
- [ ] **Order 模組**：建立訂單、查詢訂單歷史與狀態更新
- [ ] **OrderItem 明細**：多筆商品訂購與金額計算

---

## 🛠 技術棧 (Tech Stack)

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Containerization**: Docker / Docker Compose
- **Documentation**: Swagger / SpringDoc OpenAPI 2.5.0
- **Build Tool**: Maven

---

## 🏗 系統架構與規範 (Architecture & Standards)

### 統一回應格式 (Global Response)
專案採用統一的 `ApiResponse<T>` 封裝 API 回傳結果：
```json
{
  "code": 200,
  "message": "Success",
  "data": {}
}
