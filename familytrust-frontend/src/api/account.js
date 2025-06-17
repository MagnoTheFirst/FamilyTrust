import axios from 'axios';
const BASE_URL = 'http://localhost:8084';

export function createAccount(userId, data) {
    return axios.post(`${BASE_URL}/user/create/account/${userId}`, data);
}
