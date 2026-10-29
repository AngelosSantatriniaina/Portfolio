import {http} from './http';
export const publicApi={
 getProfile:async()=>(await http.get('/profile')).data,
 getExperiences:async()=>(await http.get('/experiences')).data,
 getSkills:async()=>(await http.get('/skills')).data,
 getServices:async()=>(await http.get('/services')).data,
 getProjects:async()=>(await http.get('/projects')).data,
 getReviews:async()=>(await http.get('/reviews')).data,
 sendContact:async payload=>(await http.post('/contact',payload)).data,
 addReview:async payload=>(await http.post('/reviews',payload)).data,
 addReaction:async(id,payload)=>(await http.post(`/projects/${id}/reactions`,payload)).data
};
