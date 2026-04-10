import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector
import os

# Ruta donde se guardarán las imágenes (FRONTEND)
ruta_graficas = "C:/Users/aangeles/OneDrive - Future Space/Practicas_Antonio/Practica_Final/frontend/public/graficas"

# Crear carpeta si no existe
os.makedirs(ruta_graficas, exist_ok=True)

# Conexión a la base de datos
conexion = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",
    database="PRACTICA"
)

# ==============================
# 1. AÑOS EN LA EMPRESA
# ==============================

query_anios = """
SELECT F_ALTA
FROM EM_EMPLEADOS
WHERE F_BAJA IS NULL
"""

df_anios = pd.read_sql(query_anios, conexion)

# Convertir a fecha
df_anios['F_ALTA'] = pd.to_datetime(df_anios['F_ALTA'])

# Año actual
anio_actual = pd.Timestamp.now().year

# Calcular años en la empresa
df_anios['ANIOS_EMPRESA'] = anio_actual - df_anios['F_ALTA'].dt.year

# Conteo por años
conteo_anios = df_anios['ANIOS_EMPRESA'].value_counts().sort_index()

# Gráfica
plt.figure()
conteo_anios.plot(kind='bar')

plt.title("Años en la empresa")
plt.xlabel("Años")
plt.ylabel("Número de empleados")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/anios_empresa.png")
plt.close()


# ==============================
# 2. ALTAS POR AÑO
# ==============================

query_altas = """
SELECT F_ALTA
FROM EM_EMPLEADOS
"""

df_altas = pd.read_sql(query_altas, conexion)

# Convertir a fecha
df_altas['F_ALTA'] = pd.to_datetime(df_altas['F_ALTA'])

# Extraer año
df_altas['ANIO'] = df_altas['F_ALTA'].dt.year

# Conteo por año
conteo_altas = df_altas['ANIO'].value_counts().sort_index()

# Gráfica
plt.figure()
conteo_altas.plot(kind='line', marker='o')

plt.title("Altas de empleados por año")
plt.xlabel("Año")
plt.ylabel("Número de altas")

plt.grid()
plt.tight_layout()
plt.savefig(f"{ruta_graficas}/altas_anio.png")
plt.close()


# ==============================
# 3. PROYECTOS ACTIVOS VS FINALIZADOS
# ==============================

query_proyectos = """
SELECT F_FIN
FROM PR_PROYECTOS
"""

df_proyectos = pd.read_sql(query_proyectos, conexion)

# Clasificar estado
df_proyectos['ESTADO'] = df_proyectos['F_FIN'].apply(
    lambda x: 'Finalizado' if pd.notnull(x) else 'Activo'
)

# Conteo
conteo_proyectos = df_proyectos['ESTADO'].value_counts()

# Gráfica
plt.figure()
conteo_proyectos.plot(kind='bar')

plt.title("Proyectos activos vs finalizados")
plt.xlabel("Estado")
plt.ylabel("Número de proyectos")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/proyectos_estado.png")
plt.close()


# ==============================
# 4. DURACIÓN DE PROYECTOS
# ==============================

query_duracion = """
SELECT F_INICIO, F_FIN
FROM PR_PROYECTOS
WHERE F_FIN IS NOT NULL
"""

df_duracion = pd.read_sql(query_duracion, conexion)

# Convertir fechas
df_duracion['F_INICIO'] = pd.to_datetime(df_duracion['F_INICIO'])
df_duracion['F_FIN'] = pd.to_datetime(df_duracion['F_FIN'])

# Calcular duración en días
df_duracion['DURACION'] = (df_duracion['F_FIN'] - df_duracion['F_INICIO']).dt.days

#Gráfica
plt.figure()
plt.hist(df_duracion['DURACION'], bins=10)

plt.title("Distribución de duración de proyectos (días)")
plt.xlabel("Duración")
plt.ylabel("Número de proyectos")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/duracion_proyectos.png")
plt.close()


# ==============================
# 5. EMPLEADOS POR NÚMERO DE PROYECTOS
# ==============================

query_relacion = """
SELECT ID_EMPLEADO, COUNT(ID_PROYECTO) AS NUM_PROYECTOS
FROM PR_EMPLEADOS_PROYECTO
GROUP BY ID_EMPLEADO
"""

df_relacion = pd.read_sql(query_relacion, conexion)

# Conteo: Cuántos empleados tienen X proyectos
conteo_relacion = df_relacion['NUM_PROYECTOS'].value_counts().sort_index()

# Gráfica
plt.figure()
conteo_relacion.plot(kind='bar')

plt.title("Número de proyectos por empleado")
plt.xlabel("Proyectos asignados")
plt.ylabel("Número de empleados")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/proyectos_por_empleado.png")
plt.close()


# ==============================
# 6. TOP PROYECTOS CON MÁS EMPLEADOS
# ==============================

query_top_empleados = """
SELECT ID_PROYECTO, COUNT(ID_EMPLEADO) AS NUM_EMPLEADOS
FROM PR_EMPLEADOS_PROYECTO
GROUP BY ID_PROYECTO
ORDER BY NUM_EMPLEADOS DESC
LIMIT 5
"""

df_top_empleados = pd.read_sql(query_top_empleados, conexion)

# Gráfica
plt.figure()
plt.bar(
    df_top_empleados['ID_PROYECTO'].astype(str),
    df_top_empleados['NUM_EMPLEADOS']
)

plt.title("Top proyectos con más empleados")
plt.xlabel("ID Proyecto")
plt.ylabel("Número de empleados")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/top_proyectos.png")
plt.close()


# ==============================
# 7. PROYECTOS SIN EMPLEADOS
# ==============================

query_sin_asignacion = """
SELECT P.ID_PROYECTO
FROM PR_PROYECTOS P
LEFT JOIN PR_EMPLEADOS_PROYECTO EP
ON P.ID_PROYECTO = EP.ID_PROYECTO
WHERE EP.ID_EMPLEADO IS NULL
"""

df_sin_asignacion = pd.read_sql(query_sin_asignacion, conexion)

# Número total
num_sin = len(df_sin_asignacion)

# Gráfica
datos = ["Sin empleados", "Con empleados"]
valores = [num_sin, len(df_proyectos) - num_sin]

plt.figure()
plt.bar(datos, valores)

plt.title("Proyectos sin asignaciones")
plt.ylabel("Número de proyectos")

plt.tight_layout()
plt.savefig(f"{ruta_graficas}/sin_asignacion.png")
plt.close()

# Cerrar conexión
conexion.close()