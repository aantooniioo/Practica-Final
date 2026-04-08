import axios from "axios";

const API_URL = "http://localhost:8080/empleados";

// Obtener empleados
export function getEmpleados() {
    return axios.get(API_URL);
}

// Dar de baja
export function bajaEmpleado(id){
    return axios.put(`${API_URL}/baja/${id}`);
}

// Crear empleado
export function crearEmpleado(empleado){
    return axios.post(API_URL, empleado);
}

// Editar empleado

export function getEmpleadoById(id){
    return axios.get(`${API_URL}/${id}`);
}

export function editarEmpleado(id, data){
    return axios.put(`/empleados/${id}`, data);
}
