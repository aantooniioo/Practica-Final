import axios from "axios";

const API_URL = "http://localhost:8080/proyectos";

export function getProyectos() {
    return axios.get(API_URL);
}