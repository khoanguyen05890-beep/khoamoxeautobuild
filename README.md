# KHOAMOXE AUTO BUILDER

Mod Fabric (client-side) cho Minecraft **1.21.11**, tu dong xay dung theo schematic Litematica.

**Trang thai: Giai doan 1/10** - khung project, menu ALT + chuot phai, HUD, config.
Cac chuc nang bot (build, shop, chest, ...) se duoc them o cac giai doan sau.

## Yeu cau
- JDK 21 (vi du Temurin 21)
- Fabric Loader >= 0.18.0, Fabric API 0.141.1+1.21.11

## Build tren Windows
1. Cai JDK 21 va dat `JAVA_HOME`.
2. Neu chua co `gradlew.bat`: cai Gradle 9.2.1 roi chay `gradle wrapper --gradle-version 9.2.1`
   (hoac copy `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar` tu Fabric example mod).
3. Mo CMD/PowerShell trong thu muc project:
   ```
   gradlew.bat build
   ```
4. File JAR nam o `build/libs/khoamoxe-auto-builder-0.1.0.jar` (khong lay file `-sources`).

## Cai mod
Copy JAR vao `.minecraft/mods/` cung Fabric Loader 0.18.x + Fabric API (+ Litematica o giai doan 2).

## Su dung
- **ALT + chuot phai** (khi dang trong the gioi): mo menu.
- Moi chuc nang co nut ON/OFF, luu tai `.minecraft/config/khoamoxe_autobuilder.json`.
- START / PAUSE / RESUME / STOP dieu khien bot (hien moi chi doi trang thai).
- HUD bat/tat trong menu.

## Luu y
Nhieu server cam bot/auto-build/auto-buy. Hay kiem tra luat server truoc khi dung.
