import {AlertCircle} from 'lucide-react';
export function ErrorState({message='Une erreur est survenue.',onRetry}){return <div className="state"><AlertCircle/><p>{message}</p>{onRetry&&<button className="button secondary" onClick={onRetry}>Réessayer</button>}</div>;}
