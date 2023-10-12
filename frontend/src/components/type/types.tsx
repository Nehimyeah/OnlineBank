export type UserDetails = {
    email: string;
    password: string;
    confirmPassword: string;
    firstName: string,
    lastName: string,
    street1: string,
    street2: string,
    city: string,
    state: string,
    zipcode: string,
    active: boolean,
    role: string
}

export type BranchDetails = {
    branchId: number,
    branchName: string,
    branchManagerId: number,
    address: {
        id: number,
        city: string,
        state: string,
        street1: string,
        street2: string,
        zip: string
    }
}