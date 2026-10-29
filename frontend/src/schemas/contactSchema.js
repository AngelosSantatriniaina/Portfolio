import {z} from 'zod';
export const contactSchema=z.object({name:z.string().trim().min(2,'Le nom doit contenir au moins 2 caractères.').max(100),email:z.string().trim().email('Adresse e-mail invalide.').max(180),subject:z.string().trim().min(3,'Le sujet est trop court.').max(150),message:z.string().trim().min(10,'Le message doit contenir au moins 10 caractères.').max(5000)});
