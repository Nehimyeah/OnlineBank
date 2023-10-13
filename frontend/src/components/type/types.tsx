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