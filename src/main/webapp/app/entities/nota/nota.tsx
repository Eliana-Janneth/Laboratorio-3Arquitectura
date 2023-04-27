import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INota } from 'app/shared/model/nota.model';
import { getEntities } from './nota.reducer';

export const Nota = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const notaList = useAppSelector(state => state.nota.entities);
  const loading = useAppSelector(state => state.nota.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="nota-heading" data-cy="NotaHeading">
        <Translate contentKey="labArquiJHipsterApp.nota.home.title">Notas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.nota.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/nota/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.nota.home.createLabel">Create new Nota</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {notaList && notaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.notaId">Nota Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.nota">Nota</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.fechaCreacion">Fecha Creacion</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.actividad">Actividad</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.nota.grupoEstudiante">Grupo Estudiante</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {notaList.map((nota, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nota/${nota.id}`} color="link" size="sm">
                      {nota.id}
                    </Button>
                  </td>
                  <td>{nota.notaId}</td>
                  <td>{nota.nota}</td>
                  <td>
                    {nota.fechaCreacion ? <TextFormat type="date" value={nota.fechaCreacion} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{nota.actividad ? <Link to={`/actividad/${nota.actividad.id}`}>{nota.actividad.id}</Link> : ''}</td>
                  <td>
                    {nota.grupoEstudiante ? <Link to={`/grupo-estudiante/${nota.grupoEstudiante.id}`}>{nota.grupoEstudiante.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/nota/${nota.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/nota/${nota.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/nota/${nota.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.nota.home.notFound">No Notas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Nota;
