import { IGrupo } from 'app/shared/model/grupo.model';

export interface ICurso {
  id?: number;
  cursoId?: number;
  codigo?: string;
  nombre?: string;
  credito?: number;
  grupos?: IGrupo[] | null;
}

export const defaultValue: Readonly<ICurso> = {};
