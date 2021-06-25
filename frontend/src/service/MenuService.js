import axios from "axios";

const BASE_MENU_URL = 'http://localhost:8080/restaurants/restaurant';

class MenuService {

    getMenuForToday(restaurant_id) {

        console.log("Receive today's menu for a restaurant with ID = ", restaurant_id);

        return axios.get(BASE_MENU_URL + '/' + restaurant_id + '/menus/menu');
    }
}

export default new MenuService();