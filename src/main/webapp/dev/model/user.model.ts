


export interface UserModel {

    id: number;
    name: string;
    email: string;
    password: string;
    enabled: boolean;
    roles: string[];
    registered: string;
}