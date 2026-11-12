# Build POJAV CLIENT เป็นไฟล์ JAR ผ่าน GitHub Actions

**ไฟล์ ZIP นี้ยังไม่ใช่ไฟล์ JAR สำหรับเล่น** แต่เตรียม workflow ให้ GitHub คอมไพล์โปรเจกต์และส่ง JAR กลับมาเมื่อ Build ผ่าน

1. สร้าง Repository ใหม่ที่ https://github.com
2. แตก ZIP นี้ แล้วอัปโหลดทุกอย่าง *ภายใน* โฟลเดอร์ `POJAV-CLIENT-delivery` ไปไว้ที่ราก Repository
3. ตรวจว่าหน้าแรก Repository เห็น `build.gradle`, `gradle.properties`, `src` และ `.github/workflows/build.yml`
4. เปิดแท็บ **Actions** แล้วเลือก **Build POJAV CLIENT**
5. กด **Run workflow** และรอผล
6. เมื่อสำเร็จ เปิดงาน Build แล้วดาวน์โหลด Artifact ชื่อ `POJAV-CLIENT-JAR`
7. แตก Artifact แล้วนำไฟล์ `.jar` ไปใส่ `%AppData%\\.minecraft\\mods`

ต้องใช้ Minecraft Java 1.21.11, Fabric Loader ที่รองรับเวอร์ชันนี้, Fabric API และ Java 21
การ Build ผ่านยังไม่รับประกันว่าไม่มีบั๊ก ต้องทดสอบเปิดเกมจริงด้วย
