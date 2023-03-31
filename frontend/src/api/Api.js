import axios from 'axios';

async function Api(url) {
        const response = await axios.get(url);
        return response.data;
}

export default Api;
