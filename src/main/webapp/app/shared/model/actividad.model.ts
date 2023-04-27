import { INota } from 'app/shared/model/nota.model';
import { IGrupo } from 'app/shared/model/grupo.model';

export interface IActividad {
  id?: number;
  actividadId?: number;
  descripcion?: string;
  porcentaje?: number;
  notas?: INota[] | null;
  grupo?: IGrupo | null;
}

export const defaultValue: Readonly<IActividad> = {};
