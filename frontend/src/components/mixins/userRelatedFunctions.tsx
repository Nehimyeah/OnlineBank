import * as yup from "yup";

export const userSchema = yup
    .object({
        firstName: yup.string().required(),
        lastName: yup.string().required(),
        address: yup.object({
            street1: yup.string().required(),
            street2: yup.string(),
            city: yup.string().required(),
            state: yup.string().required(),
            zip: yup.string().required(),
        }),
        email: yup.string().required().email(),
        password: yup.string().required(),
        confirmPassword: yup
            .string()
            .oneOf([yup.ref("password"), null], "Passwords must match"),
    })
    .required();

export const branchSchema = yup
    .object({
        branchName: yup.string().required(),
        branchManagerId: yup.string(),
        address: yup.object({
            street1: yup.string().required(),
            street2: yup.string(),
            city: yup.string().required(),
            state: yup.string().required(),
            zip: yup.string().required(),
        })
    })
    .required();

export const withdrawSchema = yup
    .object({
        amount: yup.number().required(),
        toAccountNum: yup.string().required()
            .min(7, 'Must be exactly 7 digits')
            .max(7, 'Must be exactly 7 digits'),
    })
    .required();

export const depositSchema = yup
    .object({
        amount: yup.string().required()
    })
    .required();