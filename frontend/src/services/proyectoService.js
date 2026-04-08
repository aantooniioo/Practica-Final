import axios from "axios";

const API_URL = "http://localhost:8080/proyectos";

export function getProyectos() {
    return axios.get(API_URL);
}

export function crearProyecto(proyecto) {
    return axios.post(API_URL, proyecto);
}

export function bajaProyecto(id) {
    return axios.put(`${API_URL}/baja/${id}`);
}
