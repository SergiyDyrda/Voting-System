

export class Restaurant {

    id: number;
    name: string;
    votes: number;

    constructor(name: string, votes: number) {
        this.name = name;
        votes != null ? this.votes = votes : this.votes = 0;
    }
}