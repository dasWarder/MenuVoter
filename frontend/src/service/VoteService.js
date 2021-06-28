import axios from "axios";


const BASE_VOTE_URL = `http://localhost:8080/restaurants/restaurant/`;


class VoteService {


    async voteForARestaurant(restaurant_id, voteDto) {
        console.log("Vote for a restaurant with ID = ", restaurant_id);
        let response;

        try {
            response = await axios.put(BASE_VOTE_URL + `${restaurant_id}/menus/menu/rate`, voteDto);
        } catch (e) {
            console.log(e);

            response = {
                data: 'Email field must be not empty!'
            };
        }

        return response;
    }
}


export default new VoteService();