export type UserDetails = {
    id: number,
    email: string;
    password: string;
    confirmPassword: string;
    firstName: string,
    lastName: string,
    address: {
        street1: string,
        street2: string,
        city: string,
        state: string,
        zip: string,
    }
    role: string;
    active: boolean;
};

export type BranchDetails = {
    branchId: number,
    branchName: string,
    branchManagerId: number | undefined,
    address: {
        id: number,
        city: string,
        state: string,
        street1: string,
        street2: string,
        zip: string
    }
}

export type Account = {
    accountNumber: string,
    accountStatus: string,
    accountType: string,
    balance: number
}

export interface RolePolicy {
    "CUSTOMER": string[],
    "MANAGER": string[],
    "TELLER": string[],
    "ADMIN": string[]
}

export type Transactions = {
    amount: number,
    createdDate: string,
    id: number,
    info: string| null
    previousBalance:number
    transactionType:string
}