import axios from "axios";

const API_URL = "http://localhost:8080/proyectos";

// Obtener proyectos
export function getProyectos() {
    return axios.get(API_URL);
}

// Crear proyecto
export function crearProyecto(proyecto) {
    return axios.post(API_URL, proyecto);
}

// Dar de baja proyecto
export function bajaProyecto(id) {
    return axios.put(`${API_URL}/baja/${id}`);
}

// Obtener proyecto
export function getProyectoById(id){
    return axios.get(`${API_URL}/${id}`);
}

// Editar proyecto
export function editarProyecto(id, data){
    return axios.put(`${API_URL}/${id}`, data);
}
