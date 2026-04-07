import axios from "axios";

const API_URL = "http://localhost:8080/asignaciones";

export function asignarEmpleado(data){
    return axios.post(API_URL, data);
}