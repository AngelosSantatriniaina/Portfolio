import {useQuery} from '@tanstack/react-query';
import {publicApi} from '../api/publicApi';
export const useProfile=()=>useQuery({queryKey:['profile'],queryFn:publicApi.getProfile});
export const useExperiences=()=>useQuery({queryKey:['experiences'],queryFn:publicApi.getExperiences});
export const useSkills=()=>useQuery({queryKey:['skills'],queryFn:publicApi.getSkills});
export const useServices=()=>useQuery({queryKey:['services'],queryFn:publicApi.getServices});
export const useProjects=()=>useQuery({queryKey:['projects'],queryFn:publicApi.getProjects});
export const useReviews=()=>useQuery({queryKey:['reviews'],queryFn:publicApi.getReviews});
