import axios from "axios";

const API_URL = "http://localhost:8080/asignaciones";

export function asignarEmpleadoProyecto(data){
    return axios.post(API_URL, data);
}

export function getAsignaciones(id) {
    return axios.get("http://localhost:8080/asignaciones");
}