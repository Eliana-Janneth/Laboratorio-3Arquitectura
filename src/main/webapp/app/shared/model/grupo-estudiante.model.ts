import { INota } from 'app/shared/model/nota.model';
import { IGrupo } from 'app/shared/model/grupo.model';
import { IEstudiante } from 'app/shared/model/estudiante.model';

export interface IGrupoEstudiante {
  id?: number;
  grupoEstudianteId?: number;
  notas?: INota[] | null;
  grupo?: IGrupo | null;
  estudiantes?: IEstudiante[] | null;
}

export const defaultValue: Readonly<IGrupoEstudiante> = {};
