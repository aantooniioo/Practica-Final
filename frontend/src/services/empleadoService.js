import axios from "axios";

const API_URL = "http://localhost:8080/empleados";

export function getEmpleados() {
    return axios.get(API_URL);
}

export function bajaEmpleado(id){
    return axios.put(`${API_URL}/baja/${id}`);
}

export function crearEmpleado(empleado){
    return axios.post(API_URL, empleado);
}