import {http} from './http';
export const adminApi={
 getProjects:async()=>(await http.get('/admin/projects')).data,
 createProject:async p=>(await http.post('/admin/projects',p)).data,
 updateProject:async(id,p)=>(await http.put(`/admin/projects/${id}`,p)).data,
 deleteProject:async id=>(await http.delete(`/admin/projects/${id}`)).data,
 getMessages:async()=>(await http.get('/admin/messages')).data,
 markMessageRead:async id=>(await http.patch(`/admin/messages/${id}/read`)).data
};
