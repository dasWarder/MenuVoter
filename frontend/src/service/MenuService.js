import axios from "axios";

const BASE_MENU_URL = 'http://localhost:8080/restaurants/restaurant';

class MenuService {

    async getMenuForToday(restaurant_id) {

        console.log("Receive today's menu for a restaurant with ID = ", restaurant_id);


        try {

            return await axios.get(BASE_MENU_URL + '/' + restaurant_id + '/menus/menu');

        } catch (e) {

           return {
               data: "Menu not found"
           };
        }

    }

    getAllMenus(restaurant_id) {

        console.log("Receiving a list of all menus for a restaurant with ID = ", restaurant_id);

        return axios.get(BASE_MENU_URL + '/' + restaurant_id + '/menus');
    }

    createTodayMenu(restaurant_id, menu) {

        console.log("Storing a menu for a restaurant with ID = ", restaurant_id);

        return axios.post(BASE_MENU_URL + '/' + restaurant_id + '/menus/menu', menu);
    }

    updateTodayMenu(restaurant_id, menu_id, menu) {

        console.log("Updating today menu for a restaurant with ID = ", restaurant_id);

        return axios.put(BASE_MENU_URL + '/' + restaurant_id + '/menus/menu/' + menu_id, menu);
    }

    deleteMenuById(restaurant_id, menu_id) {

        console.log("Removing a menu by its ID = " + menu_id + " for a restaurant with ID = " + restaurant_id);

        return axios.delete(BASE_MENU_URL + '/' + restaurant_id + '/menus/menu/' + menu_id);
    }
}

export default new MenuService();