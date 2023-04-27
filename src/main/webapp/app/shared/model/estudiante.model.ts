import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';

export interface IEstudiante {
  id?: number;
  estudianteId?: number | null;
  nombre?: string;
  gruposEstudiante?: IGrupoEstudiante | null;
}

export const defaultValue: Readonly<IEstudiante> = {};
