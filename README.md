# Kendrick Craft (NeoForge 1.21.1)

Mod para Minecraft Java 1.21.1 con NeoForge 21.1.248. No depende de otros mods.

## Compilar
1. Instala JDK 21.
2. Abre el proyecto en IntelliJ IDEA e importa Gradle, o ejecuta `./gradlew build` si el wrapper está incluido.
3. El archivo `.jar` se genera en `build/libs/`. Copíalo a la carpeta `mods` de Minecraft.

## Funciones actuales descritas por el proyecto
- `/go to sol`: viaja al Sol. En supervivencia/aventura puede ser mortal; en creativo no causa daño.
- `/go to tierra`: viaja al mundo Tierra, con bordes conectados.
- Pestaña creativa **Kendrick Craft**: incluye armas, munición, coche y avión.
- Coche: clic derecho para subir; `W/S` acelera o retrocede; `A/D` gira.
- Avión: `W` acelera; al alcanzar velocidad suficiente puede despegar y vuela hacia donde miras.
- Para recoger un vehículo, golpéalo.

## Controles
| Acción | Control |
|---|---|
| Subir al coche | Clic derecho |
| Acelerar/retroceder coche | `W` / `S` |
| Girar coche | `A` / `D` |
| Acelerar avión | `W` |
| Dirigir avión | Mirar hacia la dirección deseada |
| Recoger vehículo | Golpear el vehículo |

## Publicar en Modrinth
Sube el `.jar` como proyecto de tipo **Mod**, con loader **NeoForge** y versión de Minecraft **1.21.1**.

> Nota: este README documenta las funciones descritas en el proyecto; no afirma que las nuevas ideas de computadora interactiva, minijuego, bloques de aeropuerto o tren de aterrizaje estén implementadas.
