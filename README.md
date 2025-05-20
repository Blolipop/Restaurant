# Restarant



# Restaurant Ordering System 🍽️

- 使用者註冊與登入功能(待開發)

- 餐點菜單瀏覽

- 新增餐點至購物車

- 建立與管理訂單

管理員後台功能（如有）
---

## 🔧 技術棧

- **Spring Boot 3.4.5**
- **Thymeleaf** + Thymeleaf Layout Dialect
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Docker** (可選)
- **GitLab CI/CD** (已整合可略過測試)

---

## 📁 專案結構簡介

```
src/
├── main/
│   ├── java/com/order/Restarant/
│   │   ├── controller/     # 控制器 (MVC)
│   │   ├── entity/         # 資料模型
│   │   ├── repository/     # JPA Repository
│   │   └── RestarantApplication.java
│   └── resources/
│       ├── templates/      # Thymeleaf HTML 模板
│       ├── static/         # 靜態資源 (CSS, JS)
│       └── application.properties
```
---

## 如何執行
- 使用 Maven 打包：

``` mvn clean install -DskipTests```

- 啟動專案

``` java -jar target/Restarant-0.0.1-SNAPSHOT.jar```

或在 IDE 中執行 RestarantApplication.java

##  預設路徑
首頁	/

訂單建立	/order

菜單瀏覽	/menu

餐點管理	/dishes

##  備註
- 若在 CI/CD 中遇到測試錯誤，可使用 -DskipTests 跳過。

- 使用 PostgreSQL 資料庫，請在 application.properties 中配置連線資訊。

- 若需使用 Docker，可自建 Dockerfile 及 docker-compose.yml。

## 📸 預覽畫面 

### 訂單檢視
![首頁](screenshots/orders.png)

### 訂單建立頁面
![訂單頁](screenshots/neworders.png)

### 訂單確認 
![訂單頁](screenshots/dialogs.png)
### 菜單管理
![菜單頁](screenshots/editdish.png)


## 📜 授權
本專案僅作為展示用途，未進行授權約束。