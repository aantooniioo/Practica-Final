import axios from "axios";

const API_URL = "http://localhost:8080/asignaciones";

// Asignar empleado a proyecto
export function asignarEmpleadoProyecto(data){
    return axios.post(API_URL, data);
}

// Obtener asignaciones de empleado por id
export function getAsignaciones(id) {
    return axios.get("http://localhost:8080/asignaciones");
}