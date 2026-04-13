import axios from "axios";

// URL base del backend
const API = "http://localhost:8080/asignaciones";

/**
 * Obtiene todas las asignaciones
 */
export const getAsignaciones = () => {
  return axios.get(API);
};

/**
 * Asigna un empleado a un proyecto
 */
export const asignarEmpleadoProyecto = (datos) => {
  return axios.post(API, datos);
};

/**
 * Elimina una asignación concreta
 */
export const eliminarAsignacion = (idEmpleado, idProyecto) => {
  return axios.delete(`${API}/${idEmpleado}/${idProyecto}`);
};

/**
 * Edita la fecha de una asignación
 */
export const editarAsignacion = (idEmpleado, idProyecto, datos) => {
  return axios.put(`${API}/${idEmpleado}/${idProyecto}`, datos);
};