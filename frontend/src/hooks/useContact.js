import {useMutation,useQueryClient} from '@tanstack/react-query';
import {publicApi} from '../api/publicApi';
export function useSendContact(){const qc=useQueryClient();return useMutation({mutationFn:publicApi.sendContact,onSuccess:()=>qc.invalidateQueries({queryKey:['admin-messages']})});}
