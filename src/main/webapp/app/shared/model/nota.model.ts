import dayjs from 'dayjs';
import { IAuditoriaNota } from 'app/shared/model/auditoria-nota.model';
import { IActividad } from 'app/shared/model/actividad.model';
import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';

export interface INota {
  id?: number;
  notaId?: number;
  nota?: number;
  fechaCreacion?: string;
  auditoriasNotas?: IAuditoriaNota[] | null;
  actividad?: IActividad | null;
  grupoEstudiante?: IGrupoEstudiante | null;
}

export const defaultValue: Readonly<INota> = {};
